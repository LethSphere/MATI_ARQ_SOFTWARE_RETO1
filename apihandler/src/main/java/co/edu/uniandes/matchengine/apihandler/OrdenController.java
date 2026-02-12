package co.edu.uniandes.matchengine.apihandler;

import co.edu.uniandes.matchengine.apihandler.dto.OrdenDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrdenController {

    // Endpoint para COMPRADOR
    @PostMapping("/orden-compra")
    public ResponseEntity<OrdenDTO> recibirCompra(@RequestBody Map<String, Object> payload) {
        return procesarOrden(payload, "COMPRA");
    }

    // Endpoint para VENDEDOR
    @PostMapping("/orden-venta")
    public ResponseEntity<OrdenDTO> recibirVenta(@RequestBody Map<String, Object> payload) {
        return procesarOrden(payload, "VENTA");
    }

    // Método privado para no repetir código (DRY)
    private ResponseEntity<OrdenDTO> procesarOrden(Map<String, Object> payload, String tipo) {
        OrdenDTO orden = new OrdenDTO(
            payload.get("id").toString(),
            payload.get("producto").toString(),
            (int) payload.get("cantidad"),
            tipo
        );
        
        // Registro de salida hacia el Gestor de Eventos (AMQP)
        orden.registrarHito("apihandler_salida");

        System.out.println("Procesando " + tipo + " para el producto: " + orden.getProducto());
        
        return ResponseEntity.ok(orden);
    }
}