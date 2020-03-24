package tian.pusen.yaml.bean;

public class Computer{
    private Integer cpus;
    private String mem;

    public Integer getCpus() {
        return cpus;
    }

    public void setCpus(Integer cpus) {
        this.cpus = cpus;
    }

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "cpus=" + cpus +
                ", mem='" + mem + '\'' +
                '}';
    }
}
