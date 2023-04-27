package com.atguigu.common.oss.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OcrResult {

    /**
     * 识别出图片的文字块汇总
     */
    private String content;
    /**
     * 文字块信息
     */
    private List<PrismWordsInfo> prismWordsInfoList;

    /**
     * 识别的文字块的数量
     */
    private Integer prism_wnum;

    /**
     * 数组的大小
     */
    private Integer prism_wordsInfo;

    /**
     * 算法矫正图片后的高度
     */
    private Integer height;

    /**
     * 算法矫正图片后的宽度
     */
    private Integer width;

    /**
     * 原图的高度
     */
    private Integer orgHeight;

    /**
     * 原图的宽度
     */
    private Integer orgWidth;
}
