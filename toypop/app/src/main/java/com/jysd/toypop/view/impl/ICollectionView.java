package com.jysd.toypop.view.impl;


import com.jysd.toypop.bean.Anime;

import java.util.List;

/**
 * Created by sysadminl on 2015/12/9.
 */
public interface ICollectionView extends IBaseView {
    void setAdapter(List<Anime> list);
}
