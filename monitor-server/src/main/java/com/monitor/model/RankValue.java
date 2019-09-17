package com.monitor.model;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghui
 * Date: 14-6-26
 * Time: 上午11:04
 * To change this template use File | Settings | File Templates.
 */
public class RankValue {

    private String rank;

    private String label;

    private long values;

    private long maxs;

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    private String pos;

    public RankValue(){

    }
    public RankValue(String rank,String label,long values,long maxs,String pos){
      this.rank   = rank;
      this.label  = label;
      this.values = values;
      this.maxs   = maxs;
      this.pos = pos;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getValues() {
        return values;
    }

    public void setValues(long values) {
        this.values = values;
    }

    public long getMaxs() {
        return maxs;
    }

    public void setMaxs(long maxs) {
        this.maxs = maxs;
    }
}
