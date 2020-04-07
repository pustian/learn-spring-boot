package tian.pusen.data.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 数据项明细
 * </p>
 *
 * @author pustian
 * @since 2020-04-06
 */
@TableName("element_item")
public class ElementItem extends Model<ElementItem> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("element_id")
    private Integer elementId;
    private String name;
    @TableField("name_en")
    private String nameEn;
    @TableField("name_sql")
    private String nameSql;
    @TableField("name_c")
    private String nameC;
    @TableField("name_java")
    private String nameJava;
    @TableField("name_js")
    private String nameJs;
    private String description;
    /**
     * 0-不可用,1-使用, 9-推荐不再使用
     */
    private Integer status;
    @TableField("gmt_create")
    private Date gmtCreate;
    @TableField("gmt_modified")
    private Date gmtModified;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getElementId() {
        return elementId;
    }

    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameSql() {
        return nameSql;
    }

    public void setNameSql(String nameSql) {
        this.nameSql = nameSql;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    public String getNameJava() {
        return nameJava;
    }

    public void setNameJava(String nameJava) {
        this.nameJava = nameJava;
    }

    public String getNameJs() {
        return nameJs;
    }

    public void setNameJs(String nameJs) {
        this.nameJs = nameJs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ElementItem{" +
        ", id=" + id +
        ", elementId=" + elementId +
        ", name=" + name +
        ", nameEn=" + nameEn +
        ", nameSql=" + nameSql +
        ", nameC=" + nameC +
        ", nameJava=" + nameJava +
        ", nameJs=" + nameJs +
        ", description=" + description +
        ", status=" + status +
        ", gmtCreate=" + gmtCreate +
        ", gmtModified=" + gmtModified +
        "}";
    }
}
