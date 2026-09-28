package com.gokul.dronelocker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gokul.dronelocker.entity.PurchaseOrder;
import com.gokul.dronelocker.service.PurchaseOrderService;

@RestController
@RequestMapping("/api/purchase-orders")
@CrossOrigin
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    // Constructor injection
    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    // 1. Create purchase order
    @PostMapping
    public PurchaseOrder createPurchaseOrder(@RequestBody PurchaseOrder po) {
        return purchaseOrderService.createPurchaseOrder(po);
    }

    // 2. Get all purchase orders
    @GetMapping
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderService.getAllPurchaseOrders();
    }

    // 3. Get purchase order by ID
    @GetMapping("/{id}")
    public PurchaseOrder getPurchaseOrderById(@PathVariable Long id) {
        return purchaseOrderService.getPurchaseOrderById(id);
    }

    // 4. Update purchase order
    @PutMapping("/{id}")
    public PurchaseOrder updatePurchaseOrder(@PathVariable Long id, @RequestBody PurchaseOrder updatedPo) {
        return purchaseOrderService.updatePurchaseOrder(id, updatedPo);
    }

    // 5. Delete purchase order
    @DeleteMapping("/{id}")
    public String deletePurchaseOrder(@PathVariable Long id) {
        purchaseOrderService.deletePurchaseOrder(id);
        return "Purchase order deleted successfully";
    }
}
