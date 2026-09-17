package com.restaurant.platform.model;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("modifiers")
public class Modifier {
 @Id private String id; private String name; private String type; private Boolean required=false; private List<Option> options=new ArrayList<>();
 public Modifier() {}
 public String getId(){return id;} public void setId(String v){id=v;} public String getName(){return name;} public void setName(String v){name=v;}
 public String getType(){return type;} public void setType(String v){type=v;} public Boolean getRequired(){return required;} public void setRequired(Boolean v){required=v;}
 public List<Option> getOptions(){return options;} public void setOptions(List<Option> v){options=v;}
 public static Builder builder(){return new Builder();} public static class Builder { private final Modifier v=new Modifier();
  public Builder id(String x){v.setId(x);return this;} public Builder name(String x){v.setName(x);return this;} public Builder type(String x){v.setType(x);return this;} public Builder required(Boolean x){v.setRequired(x);return this;} public Builder options(List<Option> x){v.setOptions(x);return this;} public Modifier build(){return v;}}
 public static class Option { private String id; private String name; private BigDecimal price;
  public Option(){} public Option(String id,String name,BigDecimal price){this.id=id;this.name=name;this.price=price;}
  public String getId(){return id;} public void setId(String v){id=v;} public String getName(){return name;} public void setName(String v){name=v;} public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal v){price=v;}
 }
}