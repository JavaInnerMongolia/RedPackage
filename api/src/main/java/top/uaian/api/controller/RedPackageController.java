package top.uaian.api.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.uaian.utils.NumberUtils;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RestController
@RequestMapping("/redpackage")
public class RedPackageController {

    private volatile double total= 100.00;
    private volatile int sum = 10;



    @GetMapping("/grap")
    public String grap(){
        synchronized (RedPackageController.class){
            if(total <= 0 || sum <= 0){
                return "红包已经被抢完了";
            }
            double amount = calculateAmount(sum);
            amount = NumberUtils.round(amount, 2);
            sum--;
            total -= amount;
            log.info("剩余红包个数：" + sum);
            log.info("剩余红包金额：" + total);
            return "您抢到了" + amount;
        }
    }

    @GetMapping("/send")
    public boolean send(){
        total = 100.00;
        sum = 10;
        return true;
    }



    private double calculateAmount(int sum) {
        if(sum == 1){
            return total;
        }
        double avg = total / 10;
        double max = avg * 2;
        double min = 0.01;
        Double amount = ThreadLocalRandom.current().nextDouble(min, max);
        return amount;
    }

}
