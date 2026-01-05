package entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Parcela {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private int parcela;
	
	private LocalDate data;
	
	private LocalTime horario;
	
	@ManyToOne
	private Movimentacao movimentacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getParcela() {
		return parcela;
	}

	public void setParcela(int parcela) {
		this.parcela = parcela;
	}

	public Movimentacao getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public LocalDate getData() {
		return data;
	}

	public String getStringData() {
		String dataString = data.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
		return dataString;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public String getDiaDaSemana() {
		return data.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("PT", "BR"));
	}

	public LocalTime getHorario() {
		return horario;
	}

	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}
}
