package me.ihxq.blog.pojo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * author: HuangXiquan <br/>
 * date: 2017/10/31/031 <br/>
 * time: 23:59 <br/>
 * description:
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "tag")
public class TagDO {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @NotNull
    private String name;
    private String description;

    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinTable(name = "tag_article", joinColumns = @JoinColumn(name = "tag_id"), inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<ArticleDO> articleS;

    public TagDO(long id) {
        this.id = id;
    }

}
