import pytest
from app.app import summarize

@pytest.fixture
def sample():
    return ["1", "2", "3"]

def test_ok(sample):
    # Arrange–Act–Assert
    # Act
    #with pytest.raises(NotImplementedError):
    #    summarize(sample)
    resultados=summarize(sample)
    assert resultados=={"count": 3, "sum": 6.0, "avg": 2.0}

def test_empty():
    #with pytest.raises(Exception):
    #    summarize([])
    with pytest.raises(ValueError) as e:
        summarize([])
    assert "La lista no debe estar vacía" in str(e.value)


def test_non_numeric():
    #with pytest.raises(Exception):
    #    summarize(["a", "2"])

    with pytest.raises(ValueError) as e:
        summarize(["a", "2"])
    assert "Elemento no numérico: a" in str(e.value)
    
#python3 -m pytest -q --cov=app | tee coverage.txt
