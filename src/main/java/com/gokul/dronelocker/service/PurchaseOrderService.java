package com.gokul.dronelocker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gokul.dronelocker.entity.PurchaseOrder;
import com.gokul.dronelocker.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    // Constructor injection
    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    // 1. Create purchase order
    public PurchaseOrder createPurchaseOrder(PurchaseOrder po) {
        if (po.getStatus() == null || po.getStatus().trim().isEmpty()) {
            po.setStatus("CREATED");
        }
        if (po.getOrderDate() == null) {
            po.setOrderDate(LocalDateTime.now());
        }
        // Calculate total if not set
        if (po.getTotalAmount() == 0) {
            po.setTotalAmount(po.getQuantity() * po.getUnitPrice());
        }
        return purchaseOrderRepository.save(po);
    }

    // 2. Get all purchase orders
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    // 3. Get purchase order by ID
    public PurchaseOrder getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id).orElse(null);
    }

    // 4. Update purchase order
    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrder updatedPo) {
        PurchaseOrder existing = purchaseOrderRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setPurchaseOrderNumber(updatedPo.getPurchaseOrderNumber());
            existing.setVendorId(updatedPo.getVendorId());
            existing.setItemName(updatedPo.getItemName());
            existing.setQuantity(updatedPo.getQuantity());
            existing.setUnitPrice(updatedPo.getUnitPrice());
            existing.setTotalAmount(updatedPo.getTotalAmount());
            existing.setStatus(updatedPo.getStatus());
            return purchaseOrderRepository.save(existing);
        }
        return null;
    }

    // 5. Delete purchase order
    public void deletePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }
}
