import numpy as np
import pandas as pd
import os

print(os.getcwd())

data = pd.read_csv(os.getcwd() + "/mlib_tutorial/data/spam.csv",encoding='latin1')



labels = 1.0 * (data['v1'].values == 'ham')
labels = np.reshape(labels, (labels.shape[0], 1))
sentences = data['v2'].values
sentences = np.reshape(sentences, (sentences.shape[0], 1))
new_data = np.concatenate((labels, sentences), axis = 1)
new_data = pd.DataFrame(new_data)
new_data.to_csv("clean_data.csv")