package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var url = "https://www.omdbapi.com/?t=suits&apikey=6585022c";
		var urlEpisodios = "https://www.omdbapi.com/?t=suits&season=1&episode=2&apikey=6585022c";
		var urlTemporada = "https://www.omdbapi.com/?t=suits&season=";

		// Consumir API e guardar em um json
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados(url);

		this.print(json);

		// Instancia o conversor de dados
		ConverteDados conversor = new ConverteDados();

		// Usa o conversor para transformar o json em um dado especifico
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		printRecord(dados);

		// Dados do episodio
		json = consumoApi.obterDados(urlEpisodios);
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		printRecord(dadosEpisodio);

		// Dados temporada
		List<DadosTemporada> temporadas = new ArrayList<>();
		for(int i = 1; i<=dados.totalTemporadas(); i++) {
			json = consumoApi.obterDados(urlTemporada + i + "&apikey=6585022c");
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);

		}
		temporadas.forEach(System.out::println);
	}

	public void print(String message) {
		System.out.println(message);
	}

	public void printRecord(Record message) {
		System.out.println(message);
	}
}
