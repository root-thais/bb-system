package com.barber.systembarber.service;

import com.barber.systembarber.model.*;
import com.barber.systembarber.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável por gerenciar a lógica de agendamentos.
 * Inclui operações para criar, buscar e validar agendamentos para clientes e barbeiros.
 *
 * @author Jarmison Paiva
 */
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AvailabilityRepository availabilityRepository;
    private final UserModelRepository userModelRepository;
    private final ClienteRepository clienteRepository;
    private final OrderRepository orderRepository;

    /**
     * Cria um novo agendamento, validando a disponibilidade do barbeiro.
     *
     * @param client_id       ID do cliente que está agendando.
     * @param barber_id       ID do barbeiro escolhido.
     * @param service_id      ID do serviço a ser agendado.
     * @param appointmentStart Horário de início do agendamento.
     * @return O objeto {@link Appointment} criado e salvo.
     * @throws EntityNotFoundException se o cliente, barbeiro ou serviço não forem encontrados.
     * @throws IllegalArgumentException se o horário solicitado não estiver disponível.
     */
    public Appointment createAppointment(Long client_id,
                                         Long barber_id,
                                         Long service_id, LocalDateTime appointmentStart) {

        Cliente client = clienteRepository.findById(client_id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado com o id:" + client_id));

        UserModel barber = userModelRepository.findById(barber_id)
                .orElseThrow(() -> new EntityNotFoundException("Barbeiro não encontrado com o id:" + barber_id));

        Order service = orderRepository.findById(service_id)
                .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado com o id:" + service_id));

        LocalDateTime appointmentEnd = appointmentStart.plusMinutes(service.getDurationInMinutes());

        if (!isTimeAvailable(barber_id, appointmentStart, appointmentEnd)) {
            throw new IllegalArgumentException("O horário solicitado não está disponível para este barbeiro.");
        }

        Appointment newAppointment = buildAppointment(client, barber, service, appointmentStart, appointmentEnd);
        return appointmentRepository.save(newAppointment);
    }

    /**
     * Constrói e retorna um novo objeto Appointment.
     * Este é um método auxiliar para centralizar a criação de objetos.
     *
     * @param client          Objeto {@link Cliente} do agendamento.
     * @param barber          Objeto {@link UserModel} do barbeiro.
     * @param service         Objeto {@link Order} do serviço.
     * @param appointmentStart Horário de início.
     * @param appointmentEnd   Horário de término.
     * @return Um novo objeto {@link Appointment} com status "CONFIRMED".
     */
    private Appointment buildAppointment(Cliente client, UserModel barber, Order service,
                                         LocalDateTime appointmentStart, LocalDateTime appointmentEnd) {
        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setBarber(barber);
        appointment.setBarberOrderService(service);
        appointment.setAppointmentStart(appointmentStart);
        appointment.setAppointmentEnd(appointmentEnd);
        appointment.setStatus("CONFIRMED");
        return appointment;
    }

    /**
     * Verifica se um horário está disponível para um barbeiro.
     * Um horário é considerado disponível se:
     * 1. O barbeiro possui um slot de disponibilidade que abrange o período solicitado.
     * 2. Não há agendamentos conflitantes já existentes.
     *
     * @param barberId ID do barbeiro.
     * @param startTime Horário de início do período a ser verificado.
     * @param endTime Horário de término do período a ser verificado.
     * @return {@code true} se o horário estiver disponível, {@code false} caso contrário.
     */
    private boolean isTimeAvailable(Long barberId, LocalDateTime startTime, LocalDateTime endTime) {
        Optional<AvailabilityModel> availableSlot = availabilityRepository
                .findAvailableSlot(
                        userModelRepository.findById(barberId).orElseThrow(), startTime, endTime);

        if (availableSlot.isEmpty() || !availableSlot.get().isAvailable()) {
            return false;
        }

        List<Appointment> existingAppointments = appointmentRepository
                .findConflictingAppointments(
                        userModelRepository.findById(barberId).orElseThrow(), startTime, endTime);

        return existingAppointments.isEmpty();
    }

    /**
     * Retorna uma lista de todos os agendamentos de um barbeiro específico.
     *
     * @param barberId ID do barbeiro.
     * @return Uma lista de objetos {@link Appointment}.
     * @throws EntityNotFoundException se o barbeiro não for encontrado.
     */
    public List<Appointment> getAppointmentsByBarber(Long barberId) {
        UserModel barber = userModelRepository.findById(barberId)
                .orElseThrow(() -> new EntityNotFoundException("Barbeiro não encontrado."));
        return appointmentRepository.findByBarber(barber);
    }

    /**
     * Retorna uma lista de todos os agendamentos de um cliente específico.
     *
     * @param clientId ID do cliente.
     * @return Uma lista de objetos {@link Appointment}.
     * @throws EntityNotFoundException se o cliente não for encontrado.
     */
    public List<Appointment> getAppointmentsByClient(Long clientId) {
        Cliente client = clienteRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado."));
        return appointmentRepository.findByClient(client);
    }
}