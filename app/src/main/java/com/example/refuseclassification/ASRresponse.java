package com.example.refuseclassification;
//解裔地
import java.util.List;

/**
 * ASRresponse 类用于解析语音识别的 JSON 响应数据。
 */
public class ASRresponse {

    // 结果类型，可能的值有 "final_result" 或其他。
    private String result_type;

    // 最佳识别结果的字符串表示。
    private String best_result;

    // 原始结果对象，包含更详细的识别信息。
    private OriginResultBean origin_result;

    // 错误码，0 表示没有错误。
    private int error;

    // 识别结果列表，包含多次识别的结果。
    private List<String> results_recognition;

    // 获取结果类型。
    public String getResult_type() {
        return result_type;
    }

    // 设置结果类型。
    public void setResult_type(String result_type) {
        this.result_type = result_type;
    }

    // 获取最佳识别结果。
    public String getBest_result() {
        return best_result;
    }

    // 设置最佳识别结果。
    public void setBest_result(String best_result) {
        this.best_result = best_result;
    }

    // 获取原始结果对象。
    public OriginResultBean getOrigin_result() {
        return origin_result;
    }

    // 设置原始结果对象。
    public void setOrigin_result(OriginResultBean origin_result) {
        this.origin_result = origin_result;
    }

    // 获取错误码。
    public int getError() {
        return error;
    }

    // 设置错误码。
    public void setError(int error) {
        this.error = error;
    }

    // 获取识别结果列表。
    public List<String> getResults_recognition() {
        return results_recognition;
    }

    // 设置识别结果列表。
    public void setResults_recognition(List<String> results_recognition) {
        this.results_recognition = results_recognition;
    }

    /**
     * OriginResultBean 类包含更详细的识别信息。
     */
    public static class OriginResultBean {

        // 识别开始时间的对齐标记。
        private int asr_align_begin;

        // 识别结束时间的对齐标记。
        private int asr_align_end;

        // 语料编号。
        private long corpus_no;

        // 错误码，0 表示没有错误。
        private int err_no;

        // 识别精度相关参数。
        private int raf;

        // 结果对象，包含识别的单词列表。
        private ResultBean result;

        // 会话编号。
        private String sn;

        // 获取识别开始时间的对齐标记。
        public int getAsr_align_begin() {
            return asr_align_begin;
        }

        // 设置识别开始时间的对齐标记。
        public void setAsr_align_begin(int asr_align_begin) {
            this.asr_align_begin = asr_align_begin;
        }

        // 获取识别结束时间的对齐标记。
        public int getAsr_align_end() {
            return asr_align_end;
        }

        // 设置识别结束时间的对齐标记。
        public void setAsr_align_end(int asr_align_end) {
            this.asr_align_end = asr_align_end;
        }

        // 获取语料编号。
        public long getCorpus_no() {
            return corpus_no;
        }

        // 设置语料编号。
        public void setCorpus_no(long corpus_no) {
            this.corpus_no = corpus_no;
        }

        // 获取错误码。
        public int getErr_no() {
            return err_no;
        }

        // 设置错误码。
        public void setErr_no(int err_no) {
            this.err_no = err_no;
        }

        // 获取识别精度相关参数。
        public int getRaf() {
            return raf;
        }

        // 设置识别精度相关参数。
        public void setRaf(int raf) {
            this.raf = raf;
        }

        // 获取结果对象。
        public ResultBean getResult() {
            return result;
        }

        // 设置结果对象。
        public void setResult(ResultBean result) {
            this.result = result;
        }

        // 获取会话编号。
        public String getSn() {
            return sn;
        }

        // 设置会话编号。
        public void setSn(String sn) {
            this.sn = sn;
        }

        /**
         * ResultBean 类包含识别的单词列表。
         */
        public static class ResultBean {
            // 单词列表。
            private List<String> word;

            // 获取单词列表。
            public List<String> getWord() {
                return word;
            }

            // 设置单词列表。
            public void setWord(List<String> word) {
                this.word = word;
            }
        }
    }
}
