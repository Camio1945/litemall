package org.linlinjava.litemall.wx.service;

import java.util.List;
import org.linlinjava.litemall.db.domain.LitemallRegion;
import org.linlinjava.litemall.db.service.LitemallRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhy
 * @date 2019-01-17 23:07
 */
@Component
public class GetRegionService {

  private static List<LitemallRegion> litemallRegions;

  @Autowired private LitemallRegionService regionService;

  protected List<LitemallRegion> getLitemallRegions() {
    if (litemallRegions == null) {
      createRegion();
    }
    return litemallRegions;
  }

  private synchronized void createRegion() {
    if (litemallRegions == null) {
      litemallRegions = regionService.getAll();
    }
  }
}
