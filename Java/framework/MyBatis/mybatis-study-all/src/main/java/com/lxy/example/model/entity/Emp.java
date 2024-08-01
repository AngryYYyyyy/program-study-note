package com.lxy.example.model.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName emp
 */
@Data
public class Emp implements Serializable {
    /**
     * 
     */
    private Integer empno;

    /**
     * 
     */
    private String ename;

    /**
     * 
     */
    private String job;

    /**
     * 
     */
    private Integer mgr;

    /**
     * 
     */
    private Date hiredate;

    /**
     * 
     */
    private Double sal;

    /**
     * 
     */
    private Double comm;

    /**
     * 
     */
    private Integer deptno;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Emp other = (Emp) that;
        return (this.getEmpno() == null ? other.getEmpno() == null : this.getEmpno().equals(other.getEmpno()))
            && (this.getEname() == null ? other.getEname() == null : this.getEname().equals(other.getEname()))
            && (this.getJob() == null ? other.getJob() == null : this.getJob().equals(other.getJob()))
            && (this.getMgr() == null ? other.getMgr() == null : this.getMgr().equals(other.getMgr()))
            && (this.getHiredate() == null ? other.getHiredate() == null : this.getHiredate().equals(other.getHiredate()))
            && (this.getSal() == null ? other.getSal() == null : this.getSal().equals(other.getSal()))
            && (this.getComm() == null ? other.getComm() == null : this.getComm().equals(other.getComm()))
            && (this.getDeptno() == null ? other.getDeptno() == null : this.getDeptno().equals(other.getDeptno()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEmpno() == null) ? 0 : getEmpno().hashCode());
        result = prime * result + ((getEname() == null) ? 0 : getEname().hashCode());
        result = prime * result + ((getJob() == null) ? 0 : getJob().hashCode());
        result = prime * result + ((getMgr() == null) ? 0 : getMgr().hashCode());
        result = prime * result + ((getHiredate() == null) ? 0 : getHiredate().hashCode());
        result = prime * result + ((getSal() == null) ? 0 : getSal().hashCode());
        result = prime * result + ((getComm() == null) ? 0 : getComm().hashCode());
        result = prime * result + ((getDeptno() == null) ? 0 : getDeptno().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", empno=").append(empno);
        sb.append(", ename=").append(ename);
        sb.append(", job=").append(job);
        sb.append(", mgr=").append(mgr);
        sb.append(", hiredate=").append(hiredate);
        sb.append(", sal=").append(sal);
        sb.append(", comm=").append(comm);
        sb.append(", deptno=").append(deptno);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}