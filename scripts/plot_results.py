import pandas as pd
import matplotlib.pyplot as plt
import sys

fn = sys.argv[1] if len(sys.argv) > 1 else 'data/results.csv'
df = pd.read_csv(fn)
if df.empty:
    print('no data in', fn)
    sys.exit(1)

for algo in df['algo'].unique():
    sub = df[df['algo']==algo]
    agg = sub.groupby('n').median().reset_index()
    plt.figure()
    plt.loglog(agg['n'], agg['elapsed_ms'], marker='o')
    plt.title(f'{algo} time vs n (median)')
    plt.xlabel('n')
    plt.ylabel('elapsed ms')
    plt.grid(True)
    plt.savefig(f'{algo}.time.png')

    plt.figure()
    plt.plot(agg['n'], agg['max_rec_depth'], marker='o')
    plt.xscale('log')
    plt.title(f'{algo} depth vs n (median)')
    plt.xlabel('n')
    plt.ylabel('depth')
    plt.grid(True)
    plt.savefig(f'{algo}.depth.png')

print('plots saved')
