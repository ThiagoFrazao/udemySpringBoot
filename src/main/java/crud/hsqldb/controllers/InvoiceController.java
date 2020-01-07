package crud.hsqldb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import crud.hsqldb.models.Invoice;
import crud.hsqldb.repositories.InvoiceRepository;

@RestController
@RequestMapping("/crud/invoice")
public class InvoiceController {
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@GetMapping
	public ResponseEntity<List<Invoice>> getAllInvoices(){
		return ResponseEntity.ok((List<Invoice>)invoiceRepository.findAll());
	}
}