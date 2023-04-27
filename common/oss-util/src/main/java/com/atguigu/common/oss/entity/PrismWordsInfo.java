package com.atguigu.common.oss.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PrismWordsInfo {

    /**
     * 文字块的角度
     */
    private Integer angle;
    /**
     * 算法矫正图片后的高度
     */
    private Integer height;
    /**
     * 算法矫正图片后的宽度
     */
    private Integer width;

    /**
     * 文字块的外矩形四个点的坐标按顺时针排列（左上、右上、右下、左下）
     */
    private List pos;
    /**
     * 文字块的文字内容
     */
    private String word;
}
