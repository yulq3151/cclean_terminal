package com.cclean.terminal.util;

import com.cclean.terminal.model.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 测试用工具类
 */
public class TestUtil {

    // 获取rfid列表
    public static List<String> getRfids(int size) {
        if (size < 1) {
            return null;
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add("rfid-" + i);
        }
        return list;
    }

    // 获取sku
    public static Sku getSku() {
        Sku sku = new Sku();
        sku.setId("101");
        sku.setCode("101");
        sku.setBriefCode("101");
        sku.setName("101 浴巾");
        sku.setSize("80*140");
        sku.setType("123");
        return sku;
    }

    // 获取Sku信息列表
    public static List<Sku> getSkuList(int size){
        if (size < 1) {
            return null;
        }
        List<Sku> skuList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Sku sku = getSku();
            skuList.add(sku);
        }
        return skuList;
    }

    // 获取sku统计信息
    public static SkuStatistics getSkuStatistics() {
        SkuStatistics statistics = new SkuStatistics();
        Sku sku = getSku();
        statistics.setSku(sku);
        statistics.setCount(10);
        statistics.setActualCount(0);
        statistics.setDifferenceCount(0);
        return statistics;
    }

    // 获取Sku统计信息列表
    public static List<SkuStatistics> getSkuStatisticsList(int size){
        if (size < 1) {
            return null;
        }
        List<SkuStatistics> skuStatisticsList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            SkuStatistics statistics = getSkuStatistics();
            skuStatisticsList.add(statistics);
        }
        return skuStatisticsList;
    }

    // 获取品牌信息
    public static Brand getBrand() {
        Brand brand = new Brand();
        brand.setId("0");
        brand.setCode("123");
        brand.setName("如家");
        return brand;
    }

    // 获取品牌信息列表
    public static List<Brand> getBrandList(int size){
        if (size < 1) {
            return null;
        }
        List<Brand> BrandList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Brand brand = getBrand();
            BrandList.add(brand);
        }
        return BrandList;
    }

    // 获取供应商信息
    public static Supplier getSupplier() {
        Supplier supplier = new Supplier();
        supplier.setId("0");
        supplier.setName("供应商A");
        return supplier;
    }

    // 获取供应商信息列表
    public static List<Supplier> getSupplierList(int size){
        if (size < 1) {
            return null;
        }
        List<Supplier> supplierList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Supplier supplier = getSupplier();
            supplierList.add(supplier);
        }
        return supplierList;
    }

    // 获取产权所有者信息
    public static Property getProperty() {
        Property property = new Property();
        property.setId("0");
        property.setName("产权所有者A");
        return property;
    }

    // 获取产权所有者信息列表
    public static List<Property> getPropertyList(int size){
        if (size < 1) {
            return null;
        }
        List<Property> propertyList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Property property = getProperty();
            propertyList.add(property);
        }
        return propertyList;
    }

    // 获取酒店信息
    public static Hotel getHotel() {
        Hotel hotel = new Hotel();
        hotel.setId("0");
        hotel.setName("酒店A");
        hotel.setAddress("宝山区");
        return hotel;
    }

    // 获取酒店信息列表
    public static List<Hotel> getHotelList(int size){
        if (size < 1) {
            return null;
        }
        List<Hotel> hotelList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Hotel hotel = getHotel();
            hotelList.add(hotel);
        }
        return hotelList;
    }

    // 获取配送点信息
    public static DeliveryPoint getDeliveryPoint() {
        DeliveryPoint deliveryPoint = new DeliveryPoint();
        deliveryPoint.setId("0");
        deliveryPoint.setFloor("1");
        return deliveryPoint;
    }

    // 获取配送点信息列表
    public static List<DeliveryPoint> getDeliveryPointList(int size){
        if (size < 1) {
            return null;
        }
        List<DeliveryPoint> deliveryPointList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            DeliveryPoint deliveryPoint = getDeliveryPoint();
            deliveryPointList.add(deliveryPoint);
        }
        return deliveryPointList;
    }

    // 获取布草信息
    public static Linen getLinen() {
        Linen linen = new Linen();
        linen.setId("0");
        linen.setRfid("11");
        linen.setBatch("22");
        linen.setLastFactory("44");
        linen.setLastHotel("55");
        linen.setSku(getSku());
        linen.setLevel("A");
        linen.setProperty(getProperty());
        linen.setSupplier(getSupplier());
        linen.setWashCount(6);
        linen.setReceiveState(0);
        linen.setReceiveTime(new Date());
        return linen;
    }

    // 获取布草信息列表
    public static List<Linen> getLinenList(int size){
        if (size < 1) {
            return null;
        }
        List<Linen> linenList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Linen linen = getLinen();
            linenList.add(linen);
        }
        return linenList;
    }

    // 获取工厂信息
    public static Factory getFactory() {
        Factory factory = new Factory();
        factory.setId("0");
        factory.setName("工厂A");
        factory.setAddress("宝山");
        return factory;
    }

    // 获取工厂信息列表
    public static List<Factory> getFactoryList(int size){
        if (size < 1) {
            return null;
        }
        List<Factory> factoryList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Factory factory = getFactory();
            factoryList.add(factory);
        }
        return factoryList;
    }

    // 获取订单信息
    public static WorkOrder getWorkOrder() {
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId("0");
        workOrder.setCreateTime(new Date(System.currentTimeMillis()));
        workOrder.setDeliveryPoint(getDeliveryPoint());
        workOrder.setHotel(getHotel());
        workOrder.setModifyTime(new Date(System.currentTimeMillis()));
        workOrder.setOperator("234");
        workOrder.setOrderDate(new Date(System.currentTimeMillis()));
        workOrder.setSkuStatisticss(getSkuStatisticsList(1));
        workOrder.setState(1);
        workOrder.setType(2);
        return workOrder;
    }

    // 获取未生成任务单订单信息列表
    public static List<WorkOrder> getWorkOrderList(int size){
        if (size < 1) {
            return null;
        }
        List<WorkOrder> workOrderList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            WorkOrder workOrder = getWorkOrder();
            workOrderList.add(workOrder);
        }
        return workOrderList;
    }

    // 获取订单信息
    public static Order getOrder() {
        Order order = new Order();
        order.setId("0");
        order.setCreateTime(new Date(System.currentTimeMillis()));
        order.setDeliveryPoint(getDeliveryPoint());
        order.setHotel(getHotel());
        order.setModifyTime(new Date(System.currentTimeMillis()));
        order.setOperator("234");
        order.setOrderDate(new Date(System.currentTimeMillis()));
        order.setSkuStatisticss(getSkuStatisticsList(1));
        order.setState(1);
        order.setType(2);
        return order;
    }

    // 获取订单信息列表
    public static List<Order> getOrderList(int size){
        if (size < 1) {
            return null;
        }
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Order order = getOrder();
            orderList.add(order);
        }
        return orderList;
    }

    // 获取配货单信息
    public static DeliveryReceipt getDeliveryReceipt() {
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt();
        deliveryReceipt.setId("0");
        deliveryReceipt.setCreateTime(new Date());
        deliveryReceipt.setDeliveryPoint(getDeliveryPoint());
        deliveryReceipt.setDeliveryTime(new Date());
        deliveryReceipt.setHotel(getHotel());
        deliveryReceipt.setModifyTime(new Date());
        deliveryReceipt.setOperator("23");
        deliveryReceipt.setSkuStatisticss(getSkuStatisticsList(1));
        deliveryReceipt.setState(1);
//        deliveryReceipt.setType(1);
        return deliveryReceipt;
    }

    // 获取配货单单信息列表
    public static List<DeliveryReceipt> getDeliveryReceiptList(int size){
        if (size < 1) {
            return null;
        }
        List<DeliveryReceipt> deliveryReceiptList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            DeliveryReceipt deliveryReceipt = getDeliveryReceipt();
            deliveryReceiptList.add(deliveryReceipt);
        }
        return deliveryReceiptList;
    }

    // 获取布草打扎信息
    public static LinenPack getLinenPack() {
        LinenPack linenPack = new LinenPack();
        linenPack.setCode("22");
        linenPack.setCount(3);
        linenPack.setRfids(getRfids(1));
        linenPack.setSku(getSku());
        return linenPack;
    }


    // 获取工厂信息
    public static ScrapReason getScrapReason() {
        ScrapReason scrapReason = new ScrapReason();
        scrapReason.setId("0");
        scrapReason.setScrapReason("烟洞");
        return scrapReason;
    }

    // 获取工厂信息列表
    public static List<ScrapReason> getScrapReasonList(int size){
        if (size < 1) {
            return null;
        }
        List<ScrapReason> scrapReasonList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ScrapReason scrapReason = getScrapReason();
            scrapReasonList.add(scrapReason);
        }
        return scrapReasonList;
    }

    // 获取品牌信息
    public static LinenType getLinenType() {
        LinenType linenType = new LinenType();
        linenType.setId("0");
        linenType.setName("毛巾");
        return linenType;
    }

    // 获取品牌信息列表
    public static List<LinenType> getLinenTypeList(int size){
        if (size < 1) {
            return null;
        }
        List<LinenType> linenTypeList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            LinenType linenType = getLinenType();
            linenTypeList.add(linenType);
        }
        return linenTypeList;
    }
}
