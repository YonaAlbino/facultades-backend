package com.example.facultades.service;



import com.example.facultades.model.Notificacion;
import com.example.facultades.model.Universidad;

import java.util.List;
import java.util.Optional;

public interface INotificacionService {
    public void enviarNotificacion(String topic, String informacion);
    public void guardarNotificacionAdmin(Long idEvento, String informacion);
    public List<Notificacion> getNotificacionByIdUser(Long idUser);
    public String eliminarUsuarioNotificacion(Long idNotificacion, Long idUsuario);
    public List<Notificacion> findByLeidaFalse(Long usuarioId);
    List<Notificacion> findNotificacionesNoLeidasPorUsuario(Long userId);
    public String setNotificacionLeidaPorUsuario(Long userId);
    String visualizarNotificacionesByUserID( Long userId);
}
