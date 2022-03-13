package org.mixed.exam.classfication.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classification {
    @Id
    private String id; //分类主键
    private String classifyName; //分类名称
    private String superClassId; //分类父类编号
}
