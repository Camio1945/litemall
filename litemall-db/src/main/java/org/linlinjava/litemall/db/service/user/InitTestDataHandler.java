package org.linlinjava.litemall.db.service.user;

import static java.util.stream.Collectors.toList;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.UnaryOperator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.db.dao.*;
import org.linlinjava.litemall.db.domain.*;

/**
 * 初始化测试数据
 *
 * <pre>
 *   1. 创建若干测试用户
 *   1. 创建用户
 * </pre>
 *
 * @author Camio1945
 */
public class InitTestDataHandler {
  private final Log logger = LogFactory.getLog(InitTestDataHandler.class);
  private LitemallUserMapper userMapper;
  private LitemallAddressMapper addressMapper;
  private LitemallRegionMapper regionMapper;
  private UnaryOperator<String> encryptPassword;
  private List<LitemallRegion> provinceRegions;
  private Map<Integer, List<LitemallRegion>> regionIdToChildrenMap = new HashMap<>();
  private Random random = new Random();

  public InitTestDataHandler(
      LitemallUserMapper userMapper,
      LitemallAddressMapper addressMapper,
      LitemallRegionMapper regionMapper,
      UnaryOperator<String> encryptPassword) {
    this.userMapper = userMapper;
    this.addressMapper = addressMapper;
    this.regionMapper = regionMapper;
    this.encryptPassword = encryptPassword;
  }

  public void handle() {
    initRegions();
    int totalUser = 10000;
    for (int i = 1; i <= totalUser; i++) {
      LitemallUser user = saveUser(i);
      saveAddress(user);
      logger.info("progress: " + i + "/" + totalUser);
    }
  }

  private void saveAddress(LitemallUser user) {
    LitemallAddress address = new LitemallAddress();
    address.setName("张三");
    address.setUserId(user.getId());
    LitemallRegion provinceRegion = provinceRegions.get(random.nextInt(provinceRegions.size()));
    address.setProvince(provinceRegion.getName());
    List<LitemallRegion> cityRegions = regionIdToChildrenMap.get(provinceRegion.getId());
    LitemallRegion cityRegion = cityRegions.get(random.nextInt(cityRegions.size()));
    address.setCity(cityRegion.getName());
    List<LitemallRegion> countyRegions = regionIdToChildrenMap.get(cityRegion.getId());
    LitemallRegion countyRegion = countyRegions.get(random.nextInt(countyRegions.size()));
    address.setCounty(countyRegion.getName());
    address.setAddressDetail("测试地址");
    address.setAreaCode(countyRegion.getCode().toString());
    address.setTel(user.getMobile());
    address.setIsDefault(true);
    address.setAddTime(user.getAddTime());
    address.setUpdateTime(user.getAddTime());
    address.setDeleted(false);
    addressMapper.insert(address);
  }

  private LitemallUser saveUser(int i) {
    LitemallUser user = new LitemallUser();
    user.setUsername("user" + i);
    user.setPassword(encryptPassword.apply("123456"));
    // 性别未知
    user.setGender((byte) 0);
    user.setLastLoginIp("127.0.0.1");
    user.setNickname(user.getUsername());
    user.setMobile((18066660000L + i) + "");
    user.setAvatar("");
    user.setWeixinOpenid("");
    user.setSessionKey("");
    user.setStatus((byte) 0);
    user.setAddTime(LocalDateTime.now());
    user.setUpdateTime(user.getAddTime());
    user.setDeleted(false);
    userMapper.insert(user);
    return user;
  }

  private void initRegions() {
    List<LitemallRegion> regions = regionMapper.selectByExample(new LitemallRegionExample());
    provinceRegions = regions.stream().filter(region -> region.getType() == 1).collect(toList());
    for (LitemallRegion region : regions) {
      int type = region.getType().intValue();
      if (type == 1 || type == 2) {
        List<LitemallRegion> children =
            regions.stream()
                .filter(child -> child.getPid().intValue() == region.getId())
                .collect(toList());
        regionIdToChildrenMap.put(region.getId(), children);
      }
    }
  }
}
