package com.example.devicetestingapplication;

public class Status {
    private String CPUusage="12%";
    private String Memoryusage="16%";
    private String Diskusage="14%";
    private String version="262";
    private static final int size=4;
    private static final String CPU_TITLE="CPU利用率";
    private static final String MEMORY_TITLE="内存使用率";
    private static final String DISK_TITLE="磁盘使用率";
    private static final String VERSION="版本";

    public Status() {
    }

    public String getCPUusage() {
        return CPUusage;
    }

    public void setCPUusage(String CPUusage) {
        this.CPUusage = CPUusage;
    }

    public String getMemoryusage() {
        return Memoryusage;
    }

    public void setMemoryusage(String memoryusage) {
        Memoryusage = memoryusage;
    }

    public String getDiskusage() {
        return Diskusage;
    }

    public void setDiskusage(String diskusage) {
        Diskusage = diskusage;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public static int getSize() {
        return size;
    }

    public static String getCpuTitle() {
        return CPU_TITLE;
    }

    public static String getMemoryTitle() {
        return MEMORY_TITLE;
    }

    public static String getDiskTitle() {
        return DISK_TITLE;
    }

    public static String getVERSION() {
        return VERSION;
    }
}
