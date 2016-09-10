package utils;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by govardhanreddy on 2/6/16.
 */
public class ComputeStats {
    public static void main(String[] args) throws Exception {
//        Utils.populateQuestionStats();
        System.out.println("calling blah : ");
        blah();
        System.out.println("Called blah");
        List<Integer> l = blah();

        for (Integer a : l) {
            System.out.println(" a : " + a);
        }

        l.add(20);
        for (Integer a : l) {
            System.out.println(" a : " + a);
        }
    }

    public static List<Integer> blah() {
        return ImmutableList.<Integer>builder().add(5).build();
    }

}

/*
    
 */