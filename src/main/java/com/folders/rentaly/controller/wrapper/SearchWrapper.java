package com.folders.rentaly.controller.wrapper;

import java.nio.file.Path;
import java.util.List;

import com.folders.rentaly.model.Realty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchWrapper {
    private Realty realty;
    private List<Path> files;
}
