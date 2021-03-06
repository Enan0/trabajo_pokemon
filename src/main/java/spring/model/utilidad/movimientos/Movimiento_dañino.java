package spring.model.utilidad.movimientos;

import spring.model.entidades.pokemon.Pokemon;

public class Movimiento_dañino extends Movimiento {
    public Movimiento_dañino(String nombre, double stat, int cantidad_usos){
        super(nombre,cantidad_usos);
        this.stat = stat;
    }

    @Override
    public double get_stats() {
        return super.get_stats();
    }

    @Override
    public void aplicar(Pokemon pokemon_objetivo) {
        if(this.cantidad_usos <= 0) return;
        double vida_fila = pokemon_objetivo.get_puntosVida() - this.stat;
        pokemon_objetivo.set_puntosVida(vida_fila);
        this.cantidad_usos -= 1;
    }
}
