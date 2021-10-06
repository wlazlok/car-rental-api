//package karol.wlazlo.ds.react;
//
//import karol.wlazlo.commons.repositories.DetailProductRepository;
//import karol.wlazlo.commons.repositories.ProductItemRepository;
//import karol.wlazlo.model.DetailProductItem.DetailProductItem;
//import karol.wlazlo.model.ProductItem.ProductItem;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class LoadFakeData implements CommandLineRunner {
//
//    @Autowired
//    private ProductItemRepository productItemRepository;
//
//    @Autowired
//    private DetailProductRepository detailProductRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        ProductItem productItem = new ProductItem();
//        productItem.setProductName("Ford Mustang");
//        productItem.setDayPrice(250);
//
//        DetailProductItem detailProductItem = new DetailProductItem();
//        detailProductItem.setProductionYear(2015);
//        detailProductItem.setEngine("3.7 V6");
//        detailProductItem.setPower(305);
//        detailProductItem.setDrive("tył");
//        detailProductItem.setGearbox("6 biegowa automatyczna");
//        detailProductItem.setProduct(productItem);
//        detailProductItem.setDistanceLimitPerDay(300);
//        productItem.setProductDetails(detailProductItem);
//
//        System.out.println(productItemRepository.save(productItem));
//
//    }
//}
