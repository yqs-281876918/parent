package org.mixed.exam.admin.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classification {
    private String id; //分类主键
    private String classifyName; //分类名称
    private String superClassId; //分类父类编号
}
