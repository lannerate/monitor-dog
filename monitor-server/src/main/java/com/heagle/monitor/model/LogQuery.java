
package com.heagle.monitor.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author hui.zhang
 */
public class LogQuery {
    private String start;
    private String end;
    private String level;
    private String keyWord;


    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public DBObject toQuery() throws ParseException {
        BasicDBObject query = new BasicDBObject();
        SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map gt = new HashMap();
        if (this.getStart() != null && !this.getStart().isEmpty()) {
            Date startDate = formate.parse(this.getStart());
            gt.put("$gt", startDate);
        }
        if (this.getEnd() != null && !this.getEnd().isEmpty()) {
            Date endDate = formate.parse(this.getEnd());
            gt.put("$lt", endDate);
        }
        if (StringUtils.isNotEmpty(getLevel())) {
            query.put("level", getLevel());
        }
        if (!gt.isEmpty())
            query.put("timestamp", gt);
        if (StringUtils.isNotEmpty(keyWord)) {
            Pattern pattern = Pattern.compile(keyWord);
            query.put("message", pattern);

        }
        return query;
    }
}
