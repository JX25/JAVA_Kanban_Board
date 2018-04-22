package sample;
import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable{
    private String title;
    private String priority;
    private String description;
    private String expDate;
    private static final long serialVersionUID=1L;


    Task(String title, String priority, String description,String expDate)
    {
        this.title = title;
        this.priority=priority;
        this.description=description;
        this.expDate= expDate;
    }
    Task()
    {
        title = "Example";
        priority = "High";
        description = "Lorem ipsum lorem ipsum";
        expDate = "12.12.2019";
    }
    public void setName(String newName)
    {
        title=newName;
    }
    public void setExpDate(String exp)
    {
        expDate=exp;
    }
    public void setPriority(String prio)
    {
        priority=prio;
    }
    public void setDescription(String desc)
    {
        description=desc;
    }
    public String getDescription()
    {
        return description;
    }
    public String getDate()
    {
        return expDate;
    }
    public String getPriority()
    {return priority;}

    public String getInformation()
    {
        return ("About the task: "+title+"\nExpiring date: "+expDate+"\nPriority: "+priority+"\nDescription:\n"+description);
    }


    @Override
    public String toString() {
        return title;
    }
}
