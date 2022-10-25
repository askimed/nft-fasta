# nft-genomics

nf-test plugin to provide support for bioinformatics file formats.

## Requirements

- nf-test version 0.7.0 or higher

## Setup

To use this plugin you need to activate the `nft-genomics` plugin in your `nf-test.config` file:

```
config {
  plugins {
    load "nft-genomics@1.0.0"
  }
}
```

## FASTA Files

nf-test extends `path` by a `fasta` property that can be used to read FASTA files into maps. nf-test supports also gzipped FASTA files.


### Comparing files

```Groovy
assert path('path/to/fasta1.fasta').fasta == path("path/to/fasta2.fasta'").fasta
```

### Working with individual samples

```Groovy
def sequences = path('path/to/fasta1.fasta.gz').fasta
assert "seq1" in sequences
assert !("seq8" in sequences)
assert sequences.seq1 == "AGTACGTAGTAGCTGCTGCTACGTGCGCTAGCTAGTACGTCACGACGTAGATGCTAGCTGACTCGATGC"
```
