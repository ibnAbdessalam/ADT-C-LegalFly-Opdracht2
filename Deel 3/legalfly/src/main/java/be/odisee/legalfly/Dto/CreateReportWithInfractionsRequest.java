package be.odisee.legalfly.Dto;

import be.odisee.legalfly.Domain.Infraction;
import be.odisee.legalfly.Service.ReportServiceImpl;

import java.util.List;

public class CreateReportWithInfractionsRequest {
    public String reportType;
    public String reportContent;
    public int requestId;
    public int adminId;
    public List<ReportServiceImpl.InfractionInput> infractions; //list of infractions
}
