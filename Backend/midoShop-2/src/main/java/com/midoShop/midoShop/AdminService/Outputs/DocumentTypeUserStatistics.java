package com.midoShop.midoShop.AdminService.Outputs;

public class DocumentTypeUserStatistics {

    private double IDENTITY , INVOICE , CONTRACT , CERTIFICATE , OTHER ;

    public double getIDENTITY() {
        return IDENTITY;
    }

    public void setIDENTITY(double IDENTITY) {
        this.IDENTITY = IDENTITY;
    }

    public double getINVOICE() {
        return INVOICE;
    }

    public void setINVOICE(double INVOICE) {
        this.INVOICE = INVOICE;
    }

    public double getCONTRACT() {
        return CONTRACT;
    }

    public void setCONTRACT(double CONTRACT) {
        this.CONTRACT = CONTRACT;
    }

    public double getCERTIFICATE() {
        return CERTIFICATE;
    }

    public void setCERTIFICATE(double CERTIFICATE) {
        this.CERTIFICATE = CERTIFICATE;
    }

    public double getOTHER() {
        return OTHER;
    }

    public void setOTHER(double OTHER) {
        this.OTHER = OTHER;
    }
}
