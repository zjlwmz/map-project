package com.emay.common;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;

@Table("geometry_columns")
public class GeometryColumns {
	@Column("f_table_catalog")
	private String fTableCatalog;
	@Column("f_table_schema")
	private String fTableSchema;
	@Column("f_table_name")
	private String fTableName;
	@Column("f_geometry_column")
	private String fGeometryColumn;
	@Column("coord_dimension")
	private Integer coordDimension;
	@Column("srid")
	private Integer srid;
	@Column("type")
	private String type;
	
	public String getFTableCatalog() {
		return fTableCatalog;
	}
	public void setFTableCatalog(String tableCatalog) {
		fTableCatalog = tableCatalog;
	}
	public String getFTableSchema() {
		return fTableSchema;
	}
	public void setFTableSchema(String tableSchema) {
		fTableSchema = tableSchema;
	}
	public String getFTableName() {
		return fTableName;
	}
	public void setFTableName(String tableName) {
		fTableName = tableName;
	}
	public String getFGeometryColumn() {
		return fGeometryColumn;
	}
	public void setFGeometryColumn(String geometryColumn) {
		fGeometryColumn = geometryColumn;
	}
	public Integer getCoordDimension() {
		return coordDimension;
	}
	public void setCoordDimension(Integer coordDimension) {
		this.coordDimension = coordDimension;
	}
	public Integer getSrid() {
		return srid;
	}
	public void setSrid(Integer srid) {
		this.srid = srid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
