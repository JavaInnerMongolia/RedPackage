package top.uaian.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RestController
@RequestMapping("/redpackagev2")
public class RedPackageControllerV2 {

    private volatile double total= 100.00;
    private volatile int sum = 10;

    @Resource
    RedisTemplate<String, Integer> interRedisTemplate;
    @Resource
    RedisTemplate redisTemplate;


    @GetMapping("/grap")
    public String grap(){
        if(interRedisTemplate.opsForValue().get("redPackage") <= 0 || redisTemplate.opsForSet().size("redPackageAmount") <= 0){
            return "没有红包";
        }else {
            Double redPackageAmount = (Double) redisTemplate.opsForSet().randomMember("redPackageAmount");
            String rtn = "抢到第" + interRedisTemplate.opsForValue().get("redPackage") + "红包:" + redPackageAmount;
            System.out.println(rtn);
            interRedisTemplate.opsForValue().decrement("redPackage", 1);
            return rtn;
        }

    }

    @GetMapping("/send")
    public boolean send(){
        try {
            interRedisTemplate.opsForValue().set("redPackage", sum);
            for (int i = 0; i < sum; i++) {
                Double amount = calculateAmount(sum - i);
                redisTemplate.opsForSet().add("redPackageAmount", amount);
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private double calculateAmount(int lastSum) {
        if(lastSum <= 1){
            return total;
        }
        double avg = total / lastSum;
        double max = avg * 2;
        double min = 0.01;
        Double amount = ThreadLocalRandom.current().nextDouble(min, max);
        total-=amount;
        return amount;
    }
}
