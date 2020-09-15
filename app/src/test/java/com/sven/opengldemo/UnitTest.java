package com.sven.opengldemo;
import org.junit.Test;

import org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by Sven
 * Date:2020/9/7
 * Describe:
 */
public class UnitTest {
    @Test
    public void test(){
        int[] nums1 = new int[]{4,5,9};
        int[] nums2 = new int[]{9,4,9,8,4};
        intersect(nums1,nums2);
    }
    public int[] intersect(int[] nums1, int[] nums2) {
        if(nums1.length > nums2.length){
            return intersect(nums2,nums1);
        }
        Map<Integer,Integer> map = new HashMap();
        for(int num:nums1){
            int count = map.getOrDefault(num,0) + 1;
            map.put(num,count);
        }
        int index = 0;
        for(int num:nums2){
            int count = map.getOrDefault(num,0);
            if(count > 0){
                count --;
                map.put(num,count);
                nums1[index++] = num;
            }
        }
        return Arrays.copyOfRange(nums1,0,index);
    }
}

