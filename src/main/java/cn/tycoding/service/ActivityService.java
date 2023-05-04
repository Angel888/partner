package cn.tycoding.service;

import cn.tycoding.pojo.AppActivities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import java.lang.reflect.Type;

@Service
public class ActivityService {
    @Autowired
    private AppActivities appActivities;
//    public Type GetType(){
//        return activity.getType();
//    }

}
