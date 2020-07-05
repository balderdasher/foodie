package com.mrdios.foodie.service.impl;

import com.github.pagehelper.PageHelper;
import com.mrdios.foodie.BizException;
import com.mrdios.foodie.common.bean.PageModel;
import com.mrdios.foodie.common.enums.CommentLevelEnum;
import com.mrdios.foodie.common.enums.YesOrNoEnum;
import com.mrdios.foodie.common.utils.DesensitizationUtil;
import com.mrdios.foodie.mapper.*;
import com.mrdios.foodie.pojo.*;
import com.mrdios.foodie.pojo.vo.ItemCommentCountVo;
import com.mrdios.foodie.pojo.vo.ItemCommentVo;
import com.mrdios.foodie.pojo.vo.SearchItemsVo;
import com.mrdios.foodie.pojo.vo.ShopCartVo;
import com.mrdios.foodie.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author CodePorter
 * @date 2020-06-21
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Items getItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsImg> getItemImgs(String itemId) {
        Example example = new Example(ItemsImg.class);
        example.createCriteria().andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsSpec> getItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        example.createCriteria().andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsParam getItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        example.createCriteria().andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemCommentCountVo getItemCommentCount(String itemId) {
        ItemCommentCountVo countVo = new ItemCommentCountVo();
        Integer goodCount = getCommentCountByLevel(itemId, CommentLevelEnum.GOOD.code);
        Integer normalCount = getCommentCountByLevel(itemId, CommentLevelEnum.NORMAL.code);
        Integer badCount = getCommentCountByLevel(itemId, CommentLevelEnum.BAD.code);
        countVo.setGoodCounts(goodCount);
        countVo.setNormalCounts(normalCount);
        countVo.setBadCounts(badCount);
        countVo.setTotalCounts(goodCount + normalCount + badCount);
        return countVo;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PageModel<ItemCommentVo> getItemCommentsPage(String itemId, Integer level, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<ItemCommentVo> list = itemsCommentsMapper.getItemComments(itemId, level);
        list.forEach(comment -> comment.setNickname(DesensitizationUtil.commonDisplay(comment.getNickname())));
        return PageModel.buildPageModelFromPageInfo(list, page);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PageModel<SearchItemsVo> searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String, Object> param = new HashMap<>();
        param.put("keywords", keywords);
        param.put("sort", sort);
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVo> list = itemsMapper.searchItems(param);
        return PageModel.buildPageModelFromPageInfo(list, page);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PageModel<SearchItemsVo> searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> param = new HashMap<>();
        param.put("catId", catId);
        param.put("sort", sort);
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVo> list = itemsMapper.searchItemsByThirdCat(param);
        return PageModel.buildPageModelFromPageInfo(list, page);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ShopCartVo> queryItemsBySpecIds(List<String> specIds) {
        return itemsMapper.queryItemsBySpecIds(specIds);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsSpec queryItemSpecById(String specId) {
        return itemsSpecMapper.selectByPrimaryKey(specId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String queryItemMainImgById(String itemId) {
        ItemsImg img = new ItemsImg();
        img.setIsMain(YesOrNoEnum.YES.getCode());
        img.setItemId(itemId);
        ItemsImg mainImg = itemsImgMapper.selectOne(img);
        return null != mainImg ? mainImg.getUrl() : "";
    }

    /**
     * todo：分布式锁
     * synchronized 锁方法减库存-集群环境无用且性能低下，不推荐
     * 锁数据库 导致数据库性能低下，不推荐
     * 分布式锁 zookeeper redis 推荐
     *
     * @param specId   规格id
     * @param buyCount 购买数量
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void decreaseItemSpecStock(String specId, int buyCount) {
        // 1.库存查询
        ItemsSpec spec = queryItemSpecById(specId);
        int stock = spec.getStock();
        // 2.扣减条件判断
        if (stock - buyCount < 0) {
            throw new BizException("库存不足");
        }
        // 3.扣减库存
        itemsMapper.decreaseItemSpecStock(specId, buyCount);
    }

    private Integer getCommentCountByLevel(String itemId, Integer level) {
        ItemsComments itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);
        itemsComments.setCommentLevel(level);
        return itemsCommentsMapper.selectCount(itemsComments);
    }
}
