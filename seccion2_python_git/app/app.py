# Implementa la función summarize y el CLI.
# Requisitos:
# - summarize(nums) -> dict con claves: count, sum, avg
# - Valida que nums sea lista no vacía y elementos numéricos (acepta strings convertibles a float).
# - CLI: python -m app "1,2,3" imprime: sum=6.0 avg=2.0 count=3

def summarize(nums):  # TODO: tipado opcional
    if not nums:
        raise ValueError("La lista no debe estar vacía")

    nums_verificados=[]
    for n in nums:
        try:
            num=float(n)
            nums_verificados.append(num)
        except ValueError:
            raise ValueError(f"Elemento no numérico: {n}")

    count_val=len(nums_verificados)
    sum_val=sum(nums_verificados)
    avg_val=sum_val/count_val if count_val>0 else 0
    return {"count": count_val, "sum": sum_val, "avg": avg_val}        


if __name__ == "__main__":
    import sys
    raw = sys.argv[1] if len(sys.argv) > 1 else ""
    items = [p.strip() for p in raw.split(",") if p.strip()]
    # TODO: validar items y llamar summarize, luego imprimir el formato solicitado
    try:
        resultados=summarize(items)
        print(f"sum={resultados['sum']} avg={resultados['avg']} count={resultados['count']}")
    except ValueError as e:
        print(f"Error: {e}")

    #print("TODO: implementar CLI (python -m app "1,2,3")")
