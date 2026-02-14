package main

import (
	"context"
	"log"
	"net"
	"time"

	pb "notificaciones-go/proto" // El código que genere el proto

	"google.golang.org/grpc"
)

type server struct {
	pb.UnimplementedNotificadorServer
}

func (s *server) EnviarNotificacion(ctx context.Context, in *pb.MatchRequest) (*pb.NotificacionResponse, error) {

	// CAPA DE NOTIFICACIÓN: Aquí aplicas la Goroutine para no frenar a Java
	go func(data *pb.MatchRequest) {
		ts_inicio_go := time.Now().UnixNano()

		// Usamos la variable en el log para que el compilador no se queje
		log.Printf("Notificando Match ID: %d | Producto: %d | Iniciado en: %d",
			data.Id, data.ProductoId, ts_inicio_go)
	}(in)

	// Respondemos a Java de inmediato para que el motor siga su camino
	return &pb.NotificacionResponse{Success: true}, nil
}

func main() {
	lis, _ := net.Listen("tcp", ":50051")
	s := grpc.NewServer()
	pb.RegisterNotificadorServer(s, &server{})
	log.Println("Servidor de Notificaciones Go escuchando en :50051")
	s.Serve(lis)
}
