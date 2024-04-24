package test;



import org.ypf.generic.orm.entityoper.DaoEntity;

import java.util.Date;

/**
 * DssoasNpTask entity. @author MyEclipse Persistence Tools
 */

public class DssoasNpTask implements DaoEntity {
	@Override
	public String toString() {
		return "DssoasNpTask{" +
				"taskid=" + taskid +
				", taskname='" + taskname + '\'' +
				", tasktype=" + tasktype +
				", taskpri=" + taskpri +
				", taskstarttime=" + taskstarttime +
				", taskendtime=" + taskendtime +
				", createtime=" + createtime +
				", mdftime=" + mdftime  +
				", deltime=" + deltime +
				", taskdes='" + taskdes + '\'' +
				'}';
	}
// Fields

	private Long taskid;
	private String taskname;
	private Integer tasktype;
	private Integer taskpri;
	private Date taskstarttime;
	private Date taskendtime;
	private Date createtime;
	private Date mdftime;
	private Date deltime;
	private String taskdes;

	public Long getTaskid() {
		return taskid;
	}

	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public Integer getTasktype() {
		return tasktype;
	}

	public void setTasktype(Integer tasktype) {
		this.tasktype = tasktype;
	}

	public Integer getTaskpri() {
		return taskpri;
	}

	public void setTaskpri(Integer taskpri) {
		this.taskpri = taskpri;
	}

	public Date getTaskstarttime() {
		return taskstarttime;
	}

	public void setTaskstarttime(Date taskstarttime) {
		this.taskstarttime = taskstarttime;
	}

	public Date getTaskendtime() {
		return taskendtime;
	}

	public void setTaskendtime(Date taskendtime) {
		this.taskendtime = taskendtime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getMdftime() {
		return mdftime;
	}

	public void setMdftime(Date mdftime) {
		this.mdftime = mdftime;
	}

	public Date getDeltime() {
		return deltime;
	}

	public void setDeltime(Date deltime) {
		this.deltime = deltime;
	}

	public String getTaskdes() {
		return taskdes;
	}

	public void setTaskdes(String taskdes) {
		this.taskdes = taskdes;
	}
}