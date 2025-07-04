package com.desafio.itau.backend.dto;

import java.util.DoubleSummaryStatistics;

public record EstatisticaResponseDTO(

        Long count,
        Double sum,
        Double avg,
        Double min,
        Double max

) {

    public static EstatisticaResponseDTO fromStats(DoubleSummaryStatistics stats) {
        return new EstatisticaResponseDTO(
                stats.getCount(),
                stats.getSum(),
                stats.getAverage(),
                stats.getCount() > 0 ? stats.getMin() : 0,
                stats.getCount() > 0 ? stats.getMax() : 0

        );
    }

}
