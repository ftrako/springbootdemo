package com.example.springbootdemo.controller.shiro;

import org.apache.shiro.authz.Permission;
import org.springframework.util.StringUtils;

/**
 * @author chendajian
 * @version 1.0
 * 2022-11-01 15:11
 **/
public class BitPermission implements Permission {
    private String resourceIdentify;
    private int permissionBit;
    private String instanceId;

    public BitPermission(String permissionString) {
        String[] array = permissionString.split("\\+");
        if (array.length > 1) {
            resourceIdentify = array[1];
        }
        if (StringUtils.isEmpty(resourceIdentify)) {
            resourceIdentify = "*";
        }
        if (array.length > 2) {
            permissionBit = Integer.valueOf(array[2]);
        }
        if (array.length > 3) {
            instanceId = array[3];
        }
        if (StringUtils.isEmpty(instanceId)) {
            instanceId = "*";
        }
    }

    @Override
    public boolean implies(Permission p) {
        if (!(p instanceof BitPermission)) {
            return false;
        }
        BitPermission other = (BitPermission) p;
        if (!("*".equals(this.resourceIdentify) || this.resourceIdentify.equals(other.resourceIdentify))) {
            return false;
        }
        if (!(this.permissionBit == 0 || (this.permissionBit & other.permissionBit) != 0)) {
            return false;
        }
        if (!("*".equals(this.instanceId) || this.instanceId.equals(other.instanceId))) {
            return false;
        }
        return true;
    }
}
