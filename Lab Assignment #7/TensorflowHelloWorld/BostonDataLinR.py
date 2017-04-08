'''
Adopted by: Raj Anantharaman with changes to accomodate Boston data

Original Author: Aymeric Damien
Project: https://github.com/aymericdamien/TensorFlow-Examples/
'''

from __future__ import print_function

import tensorflow as tf
import numpy
import matplotlib.pyplot as plt
rng = numpy.random


import pandas
# The columns that are available to us in the Boston data set
colnames = ['CRIM', 'ZN', 'INDUS', 'NOX', 'RM', 'AGE','DIS','TAX','PTRATIO','MEDV']

# Split the total available data into train and test datasets
train_data = pandas.read_csv('boston_train.csv', names=colnames, skiprows=1)

# Parameters
learning_rate = 0.01
training_epochs = 1000
display_step = 50

# We will try to find the relation between Age of home and Value of home
train_X = numpy.array(train_data.AGE.tolist())
train_Y = numpy.array(train_data.MEDV.tolist())

n_samples = train_X.shape[0]

# tf Graph Input
X = tf.placeholder("float")
Y = tf.placeholder("float")

# Set model weights
W = tf.Variable(rng.randn(), name="weight")
b = tf.Variable(rng.randn(), name="bias")

# Construct a linear model
pred = tf.add(tf.multiply(X, W), b)

# Mean squared error
cost = tf.reduce_sum(tf.pow(pred-Y, 2))/(2*n_samples)
# Gradient descent
optimizer = tf.train.GradientDescentOptimizer(learning_rate).minimize(cost)

# Initializing the variables
init = tf.global_variables_initializer()

# Launch the graph
with tf.Session() as sess:
    sess.run(init)

    # Fit all training data
    for epoch in range(training_epochs):
        for (x, y) in zip(train_X, train_Y):
            sess.run(optimizer, feed_dict={X: x, Y: y})

        # Display logs per epoch step
        if (epoch+1) % display_step == 0:
            c = sess.run(cost, feed_dict={X: train_X, Y:train_Y})
            print("Epoch:", '%04d' % (epoch+1), "cost=", "{:.9f}".format(c), \
                "W=", sess.run(W), "b=", sess.run(b))

    print("Optimization Finished!")
    training_cost = sess.run(cost, feed_dict={X: train_X, Y: train_Y})
    print("Training cost=", training_cost, "W=", sess.run(W), "b=", sess.run(b), '\n')

    # Graphic display
    plt.plot(train_X, train_Y, 'ro', label='Original data')
    plt.plot(train_X, sess.run(W) * train_X + sess.run(b), label='Fitted line')
    plt.legend()
    plt.show()

    # Testing example, as requested (Issue #2)
    test_data = pandas.read_csv('boston_test.csv', names=colnames, skiprows=1)
    test_X = numpy.array(test_data.AGE.tolist())
    test_Y = numpy.array(test_data.MEDV.tolist())
    tn_samples = test_X.shape[0]

    print("Testing... (Mean square loss Comparison)")
    testing_cost = sess.run(
        tf.reduce_sum(tf.pow(pred - Y, 2)) / (2 * tn_samples),
        feed_dict={X: test_X, Y: test_Y})  # same function as cost above
    print("Testing cost=", testing_cost)
    print("Absolute mean square loss difference:", abs(
        training_cost - testing_cost))

    plt.plot(test_X, test_Y, 'bo', label='Testing data')
    plt.plot(test_X, sess.run(W) * test_X + sess.run(b), label='Fitted line')
    plt.legend()
    plt.show()