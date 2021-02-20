package br.com.minhaempresa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.minhaempresa.domain.Cliente;
import br.com.minhaempresa.domain.Empresa;
import br.com.minhaempresa.domain.Recebimento;
import br.com.minhaempresa.domain.Venda;
import br.com.minhaempresa.dto.PeriodoDTO;
import br.com.minhaempresa.dto.RelatorioDTO;
import br.com.minhaempresa.login.User;
import br.com.minhaempresa.repositories.ClienteRepository;

@Service
public class RelatorioService {

	@Autowired
	private ClienteRepository clienteRepository;

	public RelatorioDTO exibir(PeriodoDTO periodo) {

		User user = UserService.authenticated();

		List<Cliente> clientes = clienteRepository.findByEmpresa(new Empresa(user.getId()));

		Integer clientesCadastrados = clientes.size(), quantidadeVendas = 0, quantidadeRecebimentos = 0;

		Float totalVendas = 0F, totalRecebimentos = 0F, totalVendasPeriodo = 0F, totalRecebimentosPeriodo = 0F;

		for (Cliente cliente : clientes) {

			for (Venda venda : cliente.getVendas()) {

				if (venda.getEstornada() == false) {
					quantidadeVendas++;
					totalVendas += venda.getValor();

					if (venda.getData().after(periodo.getDataInicial())
							&& venda.getData().before(periodo.getDataFinal())) {
						totalVendasPeriodo += venda.getValor();
					}
				}
			}

			for (Recebimento recebimento : cliente.getRecebimentos()) {
				
				if (recebimento.getEstornada() == false) {
					quantidadeRecebimentos++;
					totalRecebimentos += recebimento.getValor();

					if (recebimento.getData().after(periodo.getDataInicial())
							&& recebimento.getData().before(periodo.getDataFinal())) {
						totalRecebimentosPeriodo += recebimento.getValor();
					}
				}
			}

		}

		return RelatorioDTO.builder().clientesCadastrados(clientesCadastrados)
				.quantidadeVendas(quantidadeVendas)
				.quantidadeRecebimentos(quantidadeRecebimentos)
				.totalVendas(totalVendas)
				.totalRecebimentos(totalRecebimentos)
				.totalVendasPeriodo(totalVendasPeriodo)
				.totalRecebimentosPeriodo(totalRecebimentosPeriodo)
				.ticketMedio(totalVendas/quantidadeVendas)
				.build();
	}

}
