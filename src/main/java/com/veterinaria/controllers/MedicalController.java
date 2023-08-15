package com.veterinaria.controllers;

import com.veterinaria.entities.DoctorEntity;
import com.veterinaria.entities.MedicineEntity;
import com.veterinaria.entities.PrescriptionEntity;
import com.veterinaria.entities.RelationPrescriptionMedicine;
import com.veterinaria.services.DoctorService;
import com.veterinaria.services.MedicineService;
import com.veterinaria.services.PrescriptionService;
import com.veterinaria.services.RelationPrescriptionMedicineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical")
public class MedicalController {

    @Autowired
    PrescriptionService prescriptionService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    MedicineService medicineService;

    @Autowired
    RelationPrescriptionMedicineService relationPrescriptionMedicineService;

    @GetMapping("/prescription")
    public List<PrescriptionEntity> getAllPrescription() {
        return prescriptionService.getAll();
    }

    @PostMapping("/prescription")
    public PrescriptionEntity createPrescription(@Valid @RequestBody PrescriptionEntity prescription) {
        return prescriptionService.save(prescription);
    }

    @GetMapping("/prescription/{id}")
    public PrescriptionEntity getPrescriptionById(@PathVariable int id) {
        return prescriptionService.getById(id);
    }

    @DeleteMapping("/prescription/{id}")
    public PrescriptionEntity deletePrescription(@PathVariable int id) {
        PrescriptionEntity prescription = prescriptionService.getById(id);
        prescriptionService.deleteById(id);
        prescription.getRelations().forEach(relation -> {
            relationPrescriptionMedicineService.deleteRelationPrescriptionMedicine(relation.getId());
        });
        return prescription;
    }

    @GetMapping("/doctor")
    public List<DoctorEntity> getAllDoctor() {
        return doctorService.getAll();
    }

    @PostMapping("/doctor")
    public DoctorEntity createDoctor(@Valid @RequestBody DoctorEntity doctor) {
        return doctorService.save(doctor);
    }

    @GetMapping("/medicine")
    public List<MedicineEntity> getAllMedicine() {
        return medicineService.listAll();
    }

    @PostMapping("/medicine")
    public MedicineEntity createMedicine(@Valid @RequestBody MedicineEntity medicine) {
        return medicineService.save(medicine);
    }

    @GetMapping("/medicine/{name}")
    public MedicineEntity getMedicineByName(@PathVariable String name) {
        return medicineService.getByName(name);
    }

    @GetMapping("/relation")
    public List<RelationPrescriptionMedicine> getAllRelation() {
        return relationPrescriptionMedicineService.getAllRelationPrescriptionMedicine();
    }

    @PostMapping("/relation")
    public RelationPrescriptionMedicine createRelation(@Valid @RequestBody RelationPrescriptionMedicine relation) {
        return relationPrescriptionMedicineService.saveRelationPrescriptionMedicine(relation);
    }
}
