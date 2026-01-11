package com.demo.openai.model;

import java.util.List;

/**
 * @author Ashwani Kumar
 * Created on 11/01/26.
 */
public record CountryCities(String country, List<String> cities) {
}
