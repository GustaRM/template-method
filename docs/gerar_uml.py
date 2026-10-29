"""Gera o diagrama UML (estilo ferramenta de desenho, fundo quadriculado)."""
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
from matplotlib.patches import Rectangle, FancyArrowPatch

LH = 0.52      # altura de uma linha de texto
PAD = 0.25
NAME_H = 0.75

fig, ax = plt.subplots(figsize=(13, 10.5))
ax.set_xlim(0, 26); ax.set_ylim(0, 21); ax.set_aspect("equal"); ax.axis("off")

# fundo quadriculado
for i in range(0, 27):
    ax.plot([i, i], [0, 21], color="#E3E3E3", lw=0.6, zorder=0)
for j in range(0, 22):
    ax.plot([0, 26], [j, j], color="#E3E3E3", lw=0.6, zorder=0)

def box(cx, top, w, name, stereo=None, attrs=(), methods=()):
    n_name = 1 + (1 if stereo else 0)
    h_name = NAME_H * n_name + (0.15 if stereo else 0)
    h_attr = max(len(attrs), 1) * LH + PAD
    h_meth = max(len(methods), 1) * LH + PAD
    h = h_name + h_attr + h_meth
    x0 = cx - w / 2
    y0 = top - h
    ax.add_patch(Rectangle((x0, y0), w, h, fc="white", ec="black", lw=1.4, zorder=2))
    ax.plot([x0, x0 + w], [top - h_name, top - h_name], color="black", lw=1.2, zorder=3)
    ax.plot([x0, x0 + w], [top - h_name - h_attr, top - h_name - h_attr], color="black", lw=1.2, zorder=3)
    ty = top - 0.45
    if stereo:
        ax.text(cx, ty, f"«{stereo}»", ha="center", va="center", fontsize=9, zorder=4)
        ty -= 0.65
    ax.text(cx, ty, name, ha="center", va="center", fontsize=11, fontweight="bold", zorder=4)
    for k, a in enumerate(attrs):
        ax.text(x0 + 0.25, top - h_name - PAD / 2 - LH * (k + 0.5), a, ha="left", va="center", fontsize=8.5, zorder=4)
    for k, m in enumerate(methods):
        ax.text(x0 + 0.25, top - h_name - h_attr - PAD / 2 - LH * (k + 0.5), m, ha="left", va="center", fontsize=8.5, zorder=4)
    return dict(cx=cx, top=top, bottom=y0, left=x0, right=x0 + w)

def inherit(sub, base):
    a = FancyArrowPatch((sub["cx"], sub["top"]), (sub["cx"], base["bottom"]) if False else (sub["cx"], base["bottom"]),
                        arrowstyle="-|>", mutation_scale=22, lw=1.3, color="black", zorder=1, shrinkA=0, shrinkB=0)
    a.set_facecolor("white")
    ax.add_patch(a)

def dependency(src, dst):
    a = FancyArrowPatch((src["cx"], src["bottom"]), (dst["cx"], dst["top"]),
                        arrowstyle="->", mutation_scale=18, lw=1.2, color="black",
                        linestyle=(0, (5, 4)), zorder=1, shrinkA=0, shrinkB=0)
    ax.add_patch(a)

main = box(13, 20.4, 5, "Main", None, (), ("+ main(args : String[])",))

base = box(13, 15.2, 11.4, "SolicitacaoCredito", "abstract",
    ("- numero : int", "- solicitante : String", "- valorSolicitado : double",
     "- rendaMensal : double", "- numeroParcelas : int"),
    ("+ verificarAprovacao() : String  {final, template}",
     "+ getInfo() : String  {final, template}",
     "+ calcularParcela() : double",
     "+ calcularComprometimento() : double",
     "# atendeRequisitos() : boolean  {abstract}",
     "# getLimiteComprometimento() : double  {abstract}",
     "+ getTipo() : String  {hook}",
     "+ getters / setters"))

pf = box(4.4, 4.2, 7.8, "CreditoPessoaFisica", None,
    ("- idade : int",),
    ("# atendeRequisitos() : boolean", "# getLimiteComprometimento() : double"))
pj = box(13, 4.2, 7.8, "CreditoPessoaJuridica", None,
    ("- anosAtividade : int",),
    ("# atendeRequisitos() : boolean", "# getLimiteComprometimento() : double", "+ getTipo() : String"))
co = box(21.6, 4.2, 7.8, "CreditoConsignado", None,
    ("- beneficiarioAtivo : boolean",),
    ("# atendeRequisitos() : boolean", "# getLimiteComprometimento() : double", "+ getTipo() : String"))

dependency(main, base)
for s in (pf, pj, co):
    # linha em "cotovelo" para as subclasses laterais
    if abs(s["cx"] - base["cx"]) < 0.01:
        inherit(s, base)
    else:
        ymid = (s["top"] + base["bottom"]) / 2
        ax.plot([s["cx"], s["cx"]], [s["top"], ymid], color="black", lw=1.3, zorder=1)
        ax.plot([s["cx"], 13 + (s["cx"] - 13) * 0.0], [ymid, ymid], color="black", lw=1.3, zorder=1)
ymid = (pf["top"] + base["bottom"]) / 2
ax.plot([pf["cx"], co["cx"]], [ymid, ymid], color="black", lw=1.3, zorder=1)
ax.plot([13, 13], [ymid, pj["top"]], color="black", lw=1.3, zorder=1)
a = FancyArrowPatch((13, ymid), (13, base["bottom"]), arrowstyle="-|>", mutation_scale=22, lw=1.3,
                    color="black", zorder=1, shrinkA=0, shrinkB=0)
a.set_facecolor("white"); ax.add_patch(a)

plt.savefig("diagrama-uml.png", dpi=150, bbox_inches="tight", facecolor="white")
