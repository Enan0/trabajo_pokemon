package spring;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import spring.model.entidades.entrenador.Entrenador;
import spring.model.entidades.pokemon.Pokemon_entrenado;
import spring.model.entidades.pokemon.Pokemon_salvaje;
import spring.model.entorno.Combate;
import spring.model.entorno.ciudad.Ciudad;
import spring.model.utilidad.efectos.Confusion;
import spring.model.utilidad.efectos.Paralisis;
import spring.model.utilidad.efectos.Sueño;
import spring.model.utilidad.movimientos.Movimiento;
import spring.model.utilidad.movimientos.Movimiento_curativo;
import spring.model.utilidad.movimientos.Movimiento_dañino;
import spring.model.utilidad.movimientos.Movimiento_especial;

import java.util.ArrayList;

@SuppressWarnings("NonAsciiCharacters")
public class movimientos_test {
    public static void main(String[] args) {
        Ciudad ciudad_test = new Ciudad();
        //Efectos
        Sueño sueño = new Sueño();
        Paralisis paralisis = new Paralisis();
        Confusion confusion = new Confusion();
        //Movimientos dañino
        Movimiento_dañino Amago = new Movimiento_dañino("Amago",30,10);
        Movimiento_dañino Golpazo = new Movimiento_dañino("Golpazo",80,1);
        Movimiento_dañino Destructor = new Movimiento_dañino("Destructor",30,10);
        Movimiento_dañino Hoja_Afilada = new Movimiento_dañino("Hoja afilada",55,0);
        Movimiento_dañino Placaje = new Movimiento_dañino("Placaje",50,5);
        //Movimientos Curativos
        Movimiento_curativo Descanso = new Movimiento_curativo("Descanso",50,5);
        Movimiento_curativo Sintesis = new Movimiento_curativo("Sintesis",100,4);
        //Movimientos especiales
        Movimiento_especial Impactrueno = new Movimiento_especial("Impactrueno",1,paralisis);
        Movimiento_especial Danza_petalo = new Movimiento_especial("Danza petalo ",3,confusion);
        Movimiento_especial Canto = new Movimiento_especial("Canto ",2,sueño);

        //Pikachu
        ArrayList<Movimiento> movimientos_pikachu = new ArrayList<Movimiento>();
        movimientos_pikachu.add(Impactrueno);
        movimientos_pikachu.add(Amago);
        movimientos_pikachu.add(Golpazo);
        movimientos_pikachu.add(Descanso);
        Pokemon_salvaje Pikachu = new Pokemon_salvaje("Pikachu",80,42050.0,movimientos_pikachu,ciudad_test);
        Pikachu.set_puntosVida(60);
        //Jigglypuff
        ArrayList<Movimiento> movimientos_jigglypuff = new ArrayList<Movimiento>();
        movimientos_jigglypuff.add(Canto);
        movimientos_jigglypuff.add(Descanso);
        movimientos_jigglypuff.add(Destructor);
        Entrenador Ash = new Entrenador("Ash",ciudad_test);
        Pokemon_entrenado Jigglypuff = new Pokemon_entrenado("Jigglypuff",100,403289,Ash,movimientos_jigglypuff);
        Jigglypuff.set_puntosVida(30);
        Ash.agregar_pokemo(Jigglypuff);

        //Meganium
        ArrayList<Movimiento> movimientos_meganium = new ArrayList<Movimiento>();
        movimientos_meganium.add(Danza_petalo);
        movimientos_meganium.add(Sintesis);
        movimientos_meganium.add(Hoja_Afilada);
        movimientos_meganium.add(Placaje);
        Entrenador Misty = new Entrenador("Misty",ciudad_test);
        Pokemon_entrenado Meganium = new Pokemon_entrenado("Meganium",250,1010042,Misty,movimientos_meganium);
        Meganium.set_puntosVida(80);
        Misty.agregar_pokemo(Meganium);
        ciudad_test.agregar_pokemon_salvaje(Pikachu);
        ciudad_test.agregar_entrenador(Ash);
        ciudad_test.agregar_entrenador(Misty);

        if(!(Meganium.get_movimientos().contains(Danza_petalo))) throw new Error("fail");
        if(!(Meganium.get_movimientos().contains(Sintesis))) throw new Error("fail");
        if(!(Meganium.get_movimientos().contains(Placaje))) throw new Error("fail");
        Jigglypuff.set_puntosVida(80);
        Combate combate = new Combate();
        combate.agregar_entrenador(Ash);
        combate.agregar_entrenador(Misty);

        combate.agregar_pokemon_a_Cobate(Pikachu);
        combate.agregar_pokemon_a_Cobate(Meganium);
        combate.agregar_pokemon_a_Cobate(Jigglypuff);

        //Si Pikachu usa golpazo peleando contra Jigglypuff, este ataque deja de estar disponible.
        Pikachu.usar_movimiento(2,Jigglypuff);
        //La vida de jigglypuff despues del ataque es 0
        Pikachu.usar_movimiento(2,Jigglypuff);
        //Como la vida de Jigglypuff no bajo de 0 significa que el ataque no aplico
        if(Jigglypuff.get_puntosVida() != 0) throw new Error("fail");

        //Como jigglypuff no puede pelear porque tiene 0 de vida, magicamente
        //Se cura 1 de hp para poder usar el movimiento descanso
        Jigglypuff.set_puntosVida(1);
        Jigglypuff.usar_movimiento(1,Jigglypuff);
        //Le restamos el 1hp que le sumamos porque somos jugadores honorables
        Jigglypuff.set_puntosVida(Jigglypuff.get_puntosVida()-1);

        //Comprobamos si la vida es igual a 50
        if(Jigglypuff.get_puntosVida() != 50) throw new Error("fail");
        Jigglypuff.set_puntosVida(30);
        //Si pikachu pedir sugerencia no tiene el indice 1(amago) error
        if(!(Pikachu.es_conveniente(1,Jigglypuff))) throw new Error("fail") ;


        if(!(Pikachu.es_conveniente(1,Meganium))) throw new Error("fail");
        if(!(Pikachu.es_conveniente(2,Meganium))) throw new Error("fail");

        //Como ash no tiene ningun pokemon mas groso, usar descanso es coveniente
        if(!(Jigglypuff.es_conveniente(1,Jigglypuff))) throw new Error("fail");
        Pokemon_entrenado pokemon_mas_groso = new Pokemon_entrenado("pokemon_mas_groso",100,1010042,Ash,movimientos_pikachu);

        Ash.agregar_pokemo(pokemon_mas_groso);
        //Como ash tiene un pokemon mas groso que Jigglypuff, usar Descanso deja de ser conveniente
        if(Jigglypuff.es_conveniente(1,Jigglypuff)) throw new Error("fail");
        if(!(Jigglypuff.soy_sacrificable())) throw new Error("fail");
        Pokemon_entrenado pokemon_menos_groso = new Pokemon_entrenado("pokemon_mas_groso",100,0,Ash,movimientos_pikachu);
        pokemon_mas_groso.set_experiencia(0);
        Ash.agregar_pokemo(pokemon_menos_groso);
        //Ash tiene otros dos pokemones mas, pero no son grosos a comparacion de Jigglypuff
        if(!(Jigglypuff.es_conveniente(1,Jigglypuff))) throw new Error("fail");

        pokemon_menos_groso.set_puntosVida(0);
        //Ash tiene otros dos pokemones mas, pero no estan vivos
        if(!(Jigglypuff.es_conveniente(1,Jigglypuff))) throw new Error("fail");

        Pikachu.set_puntosVida(Pikachu.get_pVinicial());
        //Como pikachu esta por encima de la mita de vida, descanso no es conveniente
        if(Pikachu.es_conveniente(3,Pikachu)) throw new Error("fail");

        Pikachu.set_puntosVida(30);
        if(!(Pikachu.es_conveniente(3,Pikachu))) throw new Error("fail");

        //Es conveniente usar impactrueno contra Meganium
        if(!(Pikachu.es_conveniente(0,Meganium))) throw new Error("fail");
        Meganium.set_efecto(paralisis);
        //Meganiun esta bajo un efecto, y no esta normal
        if(Meganium.estoy_normal()) throw new Error("fail");

        //Usar impactrueno contra Meganium no es conveniento porque ya esta bajo un efecto
        if(Pikachu.es_conveniente(0,Meganium)) throw new Error("fail");

        //No es conveniente usar Impactrueno contra Jigglypuff
        if(Pikachu.es_conveniente(0,Jigglypuff)) throw new Error("fail");
    }
}
