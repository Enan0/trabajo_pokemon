package spring.model.utilidad.efectos;

public class Paralisis extends Efecto {
    public Paralisis() {
        this.stat = 30;
    }

    @Override
    protected void aplicar_efecto() {

    }

    @Override
    public double get_stats() {
        return 0;
    }
}
