package me.ihxq.blog.controller;

import me.ihxq.blog.service.DomainConvertService;
import me.ihxq.blog.exception.RequestUnacceptableException;
import me.ihxq.blog.pojo.entity.ArticleDO;
import me.ihxq.blog.pojo.payload.ArticleDTO;
import me.ihxq.blog.pojo.payload.Result;
import me.ihxq.blog.service.ArticleService;
import me.ihxq.blog.service.MetricService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author xq.h
 * 2019-02-06 19:10
 **/
@RestController
@RequestMapping("article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @Resource
    private MetricService metricService;

    @Resource
    private DomainConvertService domainConvertService;

    @GetMapping("/{id}")
    public Result<ArticleDO> findById(@PathVariable Long id) {
        ArticleDO find = articleService.findById(id).orElseThrow(() -> new RequestUnacceptableException("Article not found!"));
        metricService.increasePV(id);
        return new Result<>(find);
    }

    @PostMapping
    public Result save(@RequestBody ArticleDTO articleDTO) {
        ArticleDO articleDO = domainConvertService.convert(articleDTO, ArticleDO.class);
        articleService.save(articleDO);
        return new Result();
    }

    @PutMapping("publish/{articleId}")
    public Result publish(@PathVariable Long articleId) {
        ArticleDO articleDO = articleService.findById(articleId).orElseThrow(() -> new RequestUnacceptableException("Article not found!"));
        articleDO.setPublished(true);
        articleDO.setPublishTime(LocalDateTime.now());
        articleService.save(articleDO);
        return new Result();
    }

    @DeleteMapping("/{id}")
    public Result delById(@PathVariable Long id) {
        articleService.del(id);
        return new Result();
    }

}
