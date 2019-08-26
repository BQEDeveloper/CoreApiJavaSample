package models;

import java.sql.Date;

public class ActivityModel {
	public String name;
	public String description;
    public Boolean billable;
    public String code;
    public String sub;
    public Double costRate;
    public Double billRate;
    public Double tax1;
    public Double tax2;
    public Double tax3;
    public Double minimumHours;
    public String memo;
    public Boolean isActive;
    public Double overTimeBillRate;
    public String incomeAccountId;
    public String expenseAccountId;
    public String incomeAccount;
    public String expenseAccount;
    public String defaultGroupId;
    public String defaultGroup;
    public Boolean extra;
    public Object customFields;
    public String classId;
    public String Class;
    public String id;
    public Date createdOn;
    public String createdById;
    public Date lastUpdated;
    public String lastUpdatedById;
    public String version;
    public Integer objectState;
    public String token;    
}
